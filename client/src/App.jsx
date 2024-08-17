import Dashboard from "./pages/Dashboard";
import TaskDetails from "./pages/TaskDetails";
import Trash from "./pages/Trash";
import Tasks from "./pages/Tasks";
import { Routes, Route, Navigate, Outlet, useLocation } from "react-router-dom";
// import Team from "./pages/Team";
import { Toaster } from "sonner";
import Register from "./pages/Register";
import { useDispatch, useSelector } from "react-redux";
import SideBar from "./components/SideBar";
import NavBar from "./components/NavBar";
import { Fragment, useRef } from "react";
import { IoClose } from "react-icons/io5";
import { Transition } from "@headlessui/react";
import clsx from "clsx";
import { setOpenSidebar } from "./redux/slices/authSlice";
import Users from "./pages/Team";

function Layout() {
  const { user } = useSelector((state) => state.auth);
  const location = useLocation();
  return user ? (
    <div className="w-full h-screen flex flex-col md:flex-row">
      <div className="w-1/5 h-screen bg-white sticky top-0 hidden md:block ">
        {<SideBar />}
      </div>
      {<MobileSidebar />}
      <div className="flex-1 overflow-y-auto">
        {<NavBar />}
        <div className="p-4 2xl:px-10">{<Outlet />}</div>
      </div>
    </div>
  ) : (
    <Navigate to="/logIn" state={{ from: location }} replace />
  );
}

const MobileSidebar = () => {
  const { isSidebarOpen } = useSelector((state) => state.auth);
  const mobileMenuRef = useRef(null);
  const dispatch = useDispatch();

  const closeSidebar = () => {
    dispatch(setOpenSidebar(false));
  };

  return (
    <>
      <Transition
        show={isSidebarOpen}
        as={Fragment}
        enter="transition-opacity duration-700"
        enterFrom="opacity-x-10"
        enterTo="opacity-x-100"
        leave="transition-opacity duration-700"
        leaveFrom="opacity-x-100"
        leaveTo="opacity-x-0"
      >
        {(ref) => (
          <div
            ref={(node) => (mobileMenuRef.current = node)}
            className={clsx(
              "md:hidden w-full h-full bg-black/40 transition-all duration-700 transform ",
              isSidebarOpen ? "translate-x-0" : "translate-x-full"
            )}
            onClick={() => closeSidebar()}
          >
            <div className="bg-white w-3/4 h-full">
              <div className="w-full flex justify-end px-5 mt-5">
                <button
                  onClick={() => closeSidebar()}
                  className="flex justify-end items-end"
                >
                  <IoClose size={25} />
                </button>
              </div>

              <div className="-mt-10">
                <SideBar />
              </div>
            </div>
          </div>
        )}
      </Transition>
    </>
  );
};
function App() {
  return (
    <main className="w-full min-h-screen bg-[#f3f4f6]">
      <Routes>
        <Route element={<Layout />}>
          <Route path="/" element={<Navigate to={"/dashboard"} />} />
          <Route path="/dashboard" element={<Dashboard />} />
          <Route path="/tasks" element={<Tasks />} />
          <Route path="/completed/:status" element={<Tasks />} />
          <Route path="/In-progress/:status" element={<Tasks />} />
          <Route path="/todo/:status" element={<Tasks />} />
          <Route path="/team" element={<Users />} />
          <Route path="/trashed" element={<Trash />} />
          <Route path="/task/:id" element={<TaskDetails />} />
        </Route>

        <Route path="/logIn" element={<Register />} />
      </Routes>

      <Toaster richColor />
    </main>
  );
}

export default App;
