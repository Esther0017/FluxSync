import express from 'express';

const PORT = process.env.PORT || 1001;
const app = express();

app.use(express.json());

app.get("/health", (req, res) => {
   return res.status(200).json({
      "status": "ok",
      "service": "jwt-service",
      "timestamp": Date.now() / 1000
   });
});

import jwt from "jsonwebtoken";

const SECRET = "lfUfj/yyyAfYtQRmC073RkMQhPvmucDPZG71WZ7xjqL1XNy9Js2phS0aITjvWTWC";

app.post("/issue", (req, res) => {
   const { username } = req.body;
   const payload = {
      "username": username
   };

   const token = jwt.sign(payload, SECRET, {
      issuer: "FluxSync jwt Service",
      expiresIn: "7d"
   });
   return res.status(200).json({ "status": "OK", token });
});

app.get("/verify", (req, res) => {
   if (!req.query.authorization) {
      return res.status(401).json({ "status": "Unauthorized" });
   }

   const token = req.query.authorization;

   try {
      const decoded = jwt.verify(token, SECRET);
      return res.status(200).json({ "status": "OK", "payload": decoded });
   } catch (e) {
      if (e.name === "TokenExpiredError") {
         return res.status(401).json({ "status": "TokenExpired" });
      }
      return res.status(401).json({ "status": "Unauthorized" });
   }

});

app.listen(PORT, () => {
   console.log(`jwt Service is running on port -> ${PORT}`);
});
