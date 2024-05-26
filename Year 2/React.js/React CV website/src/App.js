
import './App.css';
import Home from './components/home' //import Home function from file components/home.
import profilepic from './files/profilepic.jpg'
import Navbar from './components/navbar';
import Api from './components/apifunctions'
import CrudApi from './components/crudapipage';
import GetSkills from './components/getallprogrammingskills';
import Aboutme from './components/aboutme';
import { BrowserRouter, Link, Route, Routes } from 'react-router-dom';
import Extra from './components/extra';
import CatFact from './components/catfact';
function App() {
  return (
    <div className='App-header'>
      <div className="App">
        <h3>Aspiring Full-stack Developer</h3>
        <h1>Marco Terán</h1>
        <img src={profilepic} className="profilepic-style"></img>
      </div>
      <BrowserRouter>
      <Navbar/>
        <Routes>
            <Route exact path="/" element={(<App/>)}></Route> //om ingen route specas så reroutas man till home.
            <Route path="GetSkills" element={<GetSkills/>}></Route> //detta är "routen" för att komma till crudapipage
            <Route path="Home" element={<Home/>}></Route>
            <Route path="AboutMe" element={<Aboutme/>}></Route>
            <Route path="Extra" element={<Extra/>}></Route>
            <Route path="CatFact" element={<CatFact/>}></Route>
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
