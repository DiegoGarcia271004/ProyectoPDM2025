import {
    registerController,
    loginController,
    updateIsPremiumController,
    updateCredentialsController,
    updatePasswordController,
    addNewMolarMassController,
    getMolarMassListController,
    deleteMolarMassToListController,
    requestRecoveryController,
    resetPasswordController
} from '../controllers/user.controller.js';
import express from 'express';
import validate from '../middlewares/validator.js';
import {
    UserLoginValidationRules,
    userRegisterValidationRules,
    userUpdateCredentialsValidationRules,
    userChangePasswordValidationRules
} from '../validators/user.validator.js';
import { authenticate } from '../middlewares/auth.js';

const router = express.Router();

router.post("/register", userRegisterValidationRules, validate, registerController);
router.post("/login", UserLoginValidationRules, validate, loginController);

router.put("/premium/:id", authenticate, updateIsPremiumController);
router.put("/credentials/:id", authenticate, userUpdateCredentialsValidationRules, validate, updateCredentialsController);
router.put("/change-password/:id", authenticate, userChangePasswordValidationRules, validate, updatePasswordController);

router.put("/addMolarMass/:id", authenticate, addNewMolarMassController);
router.get("/getMolarMassList/:id", authenticate, getMolarMassListController);
router.delete("/deleteMolarMass/:userId/:molarMassId", authenticate, deleteMolarMassToListController);

router.post("/request-recovery", requestRecoveryController);
router.put("/reset-password", resetPasswordController);

export default router;