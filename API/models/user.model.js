import mongoose from "mongoose";
import bcrypt from 'bcrypt'

const MolarMassSchema = new mongoose.Schema({
    name : String,
    value : Number,
    unit : String
});

const userSchema = new mongoose.Schema({
    username : { type : String, required : true, unique : true },
    email : { type : String, required : true, unique : true },
    password : { type : String },
    googleId : { type : String },
    isPremium : { type : Boolean, default : false },
    molarMassList : [MolarMassSchema] 
}, { timestamps : true });

userSchema.pre('save', async function (next) {
    
    if (this.isModified('password')) {
        this.password = await bcrypt.hash(this.password, 10)
    }

    next()
});

userSchema.methods.comparePassword = function (password) {
    return bcrypt.compare(password, this.password)
};

const User = mongoose.model('User', userSchema);

export default User;