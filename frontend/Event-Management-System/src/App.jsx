import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Login from "./Login.jsx";
import SignUp from "./SignUp.jsx";
import UserDashboard from "./UserDashboard.jsx";
import AdminDashboard from "./AdminDashboard.jsx";
import HandleRedirect from "./HandleRedirect.jsx";

function App() {

      return (
        <>
           <Router>
               <HandleRedirect/>
               <Routes>
                   <Route path="/login" element={<Login/>}/>
                   <Route path={"/signup"} element={<SignUp/>}/>
                   <Route path={"/user"} element={<UserDashboard/>}/>
                   <Route path={"/admin"} element={<AdminDashboard/>}/>
               </Routes>
           </Router>
        </>
      )
}

export default App
