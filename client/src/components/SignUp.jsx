import React from "react";
import { useForm } from "react-hook-form";
import { useState } from "react";
import { BACKEND_URL } from "../utils";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import { useRegisterMutation } from "../redux/slices/authapiSlice";
import { toast } from "sonner";

const SignUp = ({ toggleForm }) => {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [role, setRole] = useState("");
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();

  const navigate = useNavigate();
  const dispatch = useDispatch();
  const { SignUp } = useRegisterMutation();

  const submitHandler = async (e) => {
    e.preventDefault();
    const signUpData = { name, email, role };
    try {
      const response = await axios.post(
        `${BACKEND_URL}/users/register`,
        signUpData,
        {
          headers: {
            "Content-Type": "application/json",
          },
        }
      );
      if (response.status === 201) {
        console.log(
          "SignUp successful :). Teamcode sent to your email...",
          response.data
        );
        dispatch(setCredentials(response.data));
        navigate("/");
        toast.message("TeamCode sent to your your mail.");
      }
    } catch (error) {
      toast.error("Couldn't Register :(" + error.message);
    }
  };
  const handleSignupClick = () => {
    toggleForm();
  };
  const [isAdmin, setIsAdmin] = useState(false);

  const toggleAdmin = () => {
    setIsAdmin(!isAdmin);
  };
  return (
    <div className="w-full md:1/3 p-4 md:p-1 flex flex-col items-center justify-center">
      <form
        onSubmit={submitHandler}
        className="form-container w-full md:w-[400px] flex flex-col gap-y-2 bg-white px-10 pt-8 pb-10"
      >
        <p className="text-blue-600 text-3xl font-bold text-center">
          Hey There!
        </p>
        <p className="text-center text-base text-gray-700 ">
          Fill form to create a team.
        </p>
        <div className="flex flex-col ">
          <input
            type="text"
            placeholder="Full Name"
            id="input"
            value={name}
            onChange={(e) => setName(e.target.value)}
            required
          />
          <input
            type="email"
            placeholder="Email"
            id="input"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
          <input
            type="text"
            placeholder="Role eg. Backend Developer"
            id="input"
            value={role}
            onChange={(e) => setRole(e.target.value)}
            required
          />
          <button className="bg-blue-700 text-white text-sm py-2 px-12 mt-2 border rounded-md font-bold tracking-wider uppercase w-full">
            Create Team
          </button>
          <div className="flex items-center justify-center text-[12px] mt-5">
            <p className="font-semibold">Already have a team?</p>
            <button
              className="text-blue-700 font-semibold"
              onClick={handleSignupClick}
            >
              Join Team
            </button>
          </div>
        </div>
      </form>
    </div>
  );
};

export default SignUp;
