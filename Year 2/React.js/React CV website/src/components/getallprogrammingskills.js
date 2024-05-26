import { useEffect, useState } from 'react';
import '../App.css';
function GetSkills() {
  //this is the function that gets JSON objects EXTERNALLY
  //create variable of type array. state!=variable. ProgrammingSkills är en state.
  const [ProgrammingSkills, setProgrammingSkills]=useState([])
  useEffect(() => {
    fetch('https://informatik4.ei.hv.se/marcoReactAPI/api/ProgrammingLanguage') //fetch data
    .then((response) => response.json()) //then convert response to JSON format.
    .then((actualData) => { //get actual data without security info, and other things that are assoc. with JSOn response.
      console.log(actualData) //print to consolelog for debug purposes.
      setProgrammingSkills(actualData) //save data into ProgrammingSkills-array with the setProgrammingSkills set method.
    })
    .catch((err) => { //if error, catch and print to consolelog.
      console.log(err.message);
    });
  }, []); //inside [] you can write f.e GetSkills, that means that function will run again if state of ProgramminSkills is changed.
  
    return (
      <div className="App">
        <div className='skillsbody'>
            <h2 className='introbody-text'>SKILLS</h2>
            {ProgrammingSkills.map((language) => {
              var startedLearn = language.startedLearning
              var startedLearningYear = startedLearn.slice(0,4);
              var currentYear = 2023;
              var yearsExperience = currentYear-startedLearningYear;
              return (
                <div className='programcell-style'>
                  <div className='language-cell'>
                    <h4>Language</h4>
                    <h4>{language.name}</h4>
                  </div>
                  <div className='skilllevel-cell'>
                    <h4>Skill Level</h4>
                    <h4>{language.skillLevel}</h4>
                  </div>
                  <div className='startedlearning-cell'>
                    <h4>Experience</h4>
                    <h4>{yearsExperience} Years</h4>
                  </div>     
                </div>
              )
            }
            )
          }
        </div>
      </div>
    );
  }
  
  export default GetSkills;