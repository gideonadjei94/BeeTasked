import { useEffect } from "react";
import { useState } from "react";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import Login from "../components/Login";
import SignUp from "../components/SignUp";

const Register = () => {
  const { user } = useSelector((state) => state.auth);
  const navigate = useNavigate();

  console.log(user);
  useEffect(() => {
    user && navigate("/dashboard");
  }, [user]);

  const [isLogin, setIsLogin] = useState(true);

  const toggleForm = () => {
    setIsLogin((prevState) => !prevState);
  };
  return (
    <div className="w-full min-h-screen flex items-center justify-center flex-col lg:flex-row bg-[#f3f4f6]">
      <div className="w-full md:w-auto flex gap-0 md:gap-40 flex-col md:flex-row items-center justify-center">
        <div className="h-full w-full lg:w-2/3 flex flex-col items-center justify-center">
          <div className="w-full md:max-w-lg 2xl:max-w-3xl flex flex-col items-center justify-center gap-5 md:gap-y-10 2xl:-mt-20">
            <span className="flex gap-1 py-1 px-3 border rounded-full text-sm md:text-base border-gray-400 text-gray-600">
              Manage your tasks here!
            </span>
            <p className="flex flex-col gap-0 md:gap-4 text-3xl md:text-4xl 2xl:text-6xl font-black text-center text-blue-700">
              <span>BeeTasked-</span>
              <span>Manage Tasks Seamlessly</span>
            </p>
            <div className="cell">
              <div className="circle rotate-in-up-left"></div>
            </div>
          </div>
        </div>

        {/* form */}
        <div>
          {isLogin ? (
            <Login toggleForm={toggleForm} />
          ) : (
            <SignUp toggleForm={toggleForm} />
          )}
        </div>
      </div>
    </div>
  );
};

export default Register;
