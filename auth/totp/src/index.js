import express from 'express';

const PORT = process.env.PORT || 1002;
const app = express();

app.use(express.json());

app.get("/health", (req, res) => {
    return res.status(200).json({
        "status": "ok",
        "service": "totp-service",
        "timestamp": Date.now() / 1000
    });
});

import qrcode from 'qrcode';
import speakeasy from 'speakeasy';

app.get("/generateSecret", (req, res) => {
    try {
        const secret = speakeasy.generateSecret({ length: 20 }).base32;
        return res.status(200).send(secret);
    } catch (e) {
        return res.status(500).json({ "Status": "Failed to generate secret" });
    }
});

app.get("/generateQrCode", (req, res) => {
   try {
       const secret = req.query.secret;

       const url = speakeasy.otpauthURL({
           secret,
           encoding: "base32",
           label: "fluxsync@esther.com",
           issuer: "FluxSync"
       });

       qrcode.toDataURL(url, (err, dataUrl) => {

           if (err) {
               // 错误处理
               return res.status(500).json({ "Status": "Failed to generate QR code" });
           }

          return res.status(200).json({ "Status": dataUrl });
       });
   } catch (e) {
       return res.status(500).json({ "Status": "Failed to generate QrCode" });
   }
});

app.get("/verifyTOTP", (req, res) => {

    const { secret, token } = req.query;

    if (secret == null || token == null) return res.status(401).json({ "Status": "Unauthorized" });

    try {

        const isValid = speakeasy.totp.verify({
           secret,
           token,
           encoding: 'base32',
           window: 1
        });

        if (isValid) {
           return res.status(200).json({ "Status": "ok" });
        } else {
           return res.status(401).json({ "Status": "Unauthorized" });
        }

    } catch (e) {
       return res.status(401).json({ "Status": "Unauthorized" });
    }

});

app.listen(PORT, () => {
    console.log(`totp Service is running on port -> ${PORT}`);
});