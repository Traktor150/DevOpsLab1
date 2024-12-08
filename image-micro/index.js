import express from 'express';
import { Sequelize, DataTypes } from 'sequelize';
import multer from 'multer';
import { v2 as cloudinary } from 'cloudinary';
import streamifier from 'streamifier';
import dotenv from 'dotenv';

dotenv.config();

const app = express();
const upload = multer();

app.use(express.json({ limit: '10mb' }));
app.use(express.urlencoded({ extended: false }));

// Cloudinary configuration
cloudinary.config({
  cloud_name: process.env.CLOUDINARY_CLOUD_NAME,
  api_key: process.env.CLOUDINARY_API_KEY,
  api_secret: process.env.CLOUDINARY_API_SECRET,
});

// Sequelize setup
const sequelize = new Sequelize(
  process.env.DB_NAME,
  process.env.DB_USER,
  process.env.DB_PASSWORD,
  {
    host: process.env.DB_HOST,
    dialect: 'mysql',
  }
);

// Test DB connection
sequelize.authenticate().then(() => console.log('Database connected!'));

// Image model
const Image = sequelize.define(
  'Image',
  {
    id: {
      type: DataTypes.BIGINT,
      primaryKey: true,
      autoIncrement: true,
    },
    practitionerId: {
      type: DataTypes.BIGINT,
      allowNull: false,
    },
    cloudinaryPublicId: {
      type: DataTypes.STRING,
      allowNull: false,
    },
    imageUrl: {
      type: DataTypes.STRING,
      allowNull: false,
    },
  },
  {
    tableName: 'Image',
    timestamps: true,
  }
);

// Helper function for Cloudinary upload
const uploadToCloudinary = (buffer) => {
  console.log('Inside');
  return new Promise((resolve, reject) => {
    const uploadStream = cloudinary.uploader.upload_stream(
      { folder: 'medical_images' },
      (error, result) => {
        if (error) reject(error);
        else resolve(result);
      }
    );
    streamifier.createReadStream(buffer).pipe(uploadStream);
  });
};

// 1. Upload an image
app.post('/api/images/upload', upload.single('image'), async (req, res) => {
  try {
    if (!req.file) {
      return res.status(400).json({ error: 'No image file provided' });
    }

    const result = await uploadToCloudinary(req.file.buffer);

    const image = await Image.create({
      practitionerId: req.body.practitionerId,
      cloudinaryPublicId: result.public_id,
      imageUrl: result.secure_url,
    });

    res.status(201).json(image);
  } catch (error) {
    console.error('Error uploading image:', error);
    res.status(500).json({ error: 'Error uploading image' });
  }
});

// 2. Retrieve an image
app.get('/api/images/single/:id', async (req, res) => {
  try {
    const image = await Image.findByPk(req.params.id);
    if (!image) {
      return res.status(404).json({ error: 'Image not found' });
    }
    res.json(image);
  } catch (error) {
    console.error('Error retrieving image:', error);
    res.status(500).json({ error: 'Error retrieving image' });
  }
});

// 3. Retrieve all images based on doctor id
app.get('/api/images/practitioner/:practitionerId', async (req, res) => {
  try {
    const images = await Image.findAll({
      where: { practitionerId: req.params.practitionerId },
    });
    res.json(images);
  } catch (error) {
    console.error('Error retrieving images:', error);
    res.status(500).json({ error: 'Error retrieving images' });
  }
});

// 4. Upload edited image in Cloudinary (overriding the previous image)
app.put('/api/images/edit/:id', upload.single('image'), async (req, res) => {
  try {
    const image = await Image.findByPk(req.params.id);
    if (!image) {
      return res.status(404).json({ error: 'Image not found' });
    }

    if (!req.file) {
      return res.status(400).json({ error: 'No image file provided' });
    }

    // Delete the previous image from Cloudinary
    await cloudinary.uploader.destroy(image.cloudinaryPublicId);

    // Upload the new image
    const result = await uploadToCloudinary(req.file.buffer);

    // Update the image record
    await image.update({
      cloudinaryPublicId: result.public_id,
      imageUrl: result.secure_url,
    });

    res.json(image);
  } catch (error) {
    console.error('Error updating image:', error);
    res.status(500).json({ error: 'Error updating image' });
  }
});

app.get('/hello', (req, res) => {
  res.send('Hello from server');
});

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
  console.log(`Server is running on port ${PORT}`);
});
