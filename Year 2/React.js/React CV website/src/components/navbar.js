import { Link } from "react-router-dom";
function Navbar () {
    return (
        <div className="navbar-style">
            <div className="link-div">
                <Link to="/GetSkills" className='navbar-link-style'>My Skills</Link>
            </div>
            <div className="link-div">
                <Link to="/AboutMe" className='navbar-link-style'>About Me</Link>
            </div>
            <div className="link-div">
                <Link to="/Home" className='navbar-link-style'>Home</Link>
            </div>
            <div className="link-div">
                <Link to="/Extra" className='navbar-link-style'>SOS100 Group API</Link>
            </div>
            <div className="link-div">
                <Link to="/CatFact" className='navbar-link-style'>Random cat fact</Link>
            </div>
        </div>
    );
}

export default Navbar;