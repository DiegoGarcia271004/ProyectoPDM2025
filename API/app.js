import express from 'express';
//import cors from 'cors';
import router from './routes/user.route.js';
//import { config } from './config/config';

const app = express();

app.use(express.json());
app.use("/api/SeaShellCalculator", router);

export default app;