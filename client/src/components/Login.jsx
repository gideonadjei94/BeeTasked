import React, { useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import { useLoginMutation } from "../redux/slices/authapiSlice";
import { toast } from "sonner";
import { useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { setCredentials } from "../redux/slices/authSlice";
import axios from "axios";
import { BACKEND_URL } from "../utils";

const Login = ({ toggleForm }) => {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [role, setRole] = useState("");
  const [teamCode, setTeamCode] = useState("");
  const { user } = useSelector((state) => state.auth);
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();

  const navigate = useNavigate();
  const dispatch = useDispatch();
  const [login, { isLoading }] = useLoginMutation();

  const submitHandler = async (e) => {
    e.preventDefault();
    const loginData = { name, email, role, teamCode };
    try {
      const response = await axios.post(
        `${BACKEND_URL}/users/login`,
        loginData,
        {
          headers: {
            "Content-Type": "application/json",
          },
        }
      );
      if (response.status === 201) {
        console.log("Login successful:", response.data);
        dispatch(setCredentials(response.data));
        navigate("/");
      }
    } catch (error) {
      toast.error("Herh Chale.." + error.message);
    }
  };

  const handleSignupClick = () => {
    toggleForm();
  };
  return (
    <div className="w-full md:1/3 p-4 md:p-1 flex flex-col items-center justify-center">
      <form
        onSubmit={submitHandler}
        className="form-container w-full md:w-[400px] flex flex-col gap-y-2 bg-white px-10 pt-8 pb-10"
      >
        <div className="">
          <p className="text-blue-600 text-3xl font-bold text-center">
            Welcome!
          </p>
          <p className="text-center text-base text-gray-700 ">
            Sign In to Join team.
          </p>
        </div>
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
            placeholder="Role eg. Frontend Developer"
            id="input"
            value={role}
            onChange={(e) => setRole(e.target.value)}
            required
          />
          <input
            type="password"
            placeholder="Team Code"
            id="input"
            value={teamCode}
            onChange={(e) => setTeamCode(e.target.value)}
            required
          />

          <button className="bg-blue-700 text-white text-sm py-2 px-12 mt-2 border rounded-md font-bold tracking-wider uppercase w-full">
            Join Team
          </button>
          <div className="flex items-center justify-center text-[12px] mt-5">
            <p className="font-semibold">Want to create a team?</p>
            <button
              className="text-blue-700 font-semibold"
              onClick={handleSignupClick}
            >
              Create Team
            </button>
          </div>
        </div>
      </form>
    </div>
  );
};

export default Login;
