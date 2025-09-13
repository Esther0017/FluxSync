import express from 'express';
import cors from 'cors';
import dotenv from 'dotenv';

const app = express();

app.use(cors({origin: '*'}));

dotenv.config();

app.listen(process.env.PORT || 1001, () => {
   console.log(`jwt 服务启动正常. port: ${process.env.PORT || 1001}`);
});
