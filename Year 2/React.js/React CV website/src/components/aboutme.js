import { useEffect, useState } from 'react';
import '../App.css';
function Aboutme() {

  const [person, setPerson]=useState([])
  useEffect(() => {
    fetch('http://localhost:5244/api/People') 
    .then((response) => response.json())
    .then((actualData) => { 
      console.log(actualData) 
      setPerson(actualData) 
    })
    .catch((err) => { 
      console.log(err.message);
    });
  }, []); 

    return (
      <div className="App">
        <div className='skillsbody'>
            <h2 className="introbody-text">ABOUT</h2>
            {person.map((me) => {
              return (

                    <div className='infocell-style'>
                        <div className='infocell-header'>
                            <div className='name-cell'>
                                <h3>Name</h3>
                                <h4>{me.name}</h4>
                            </div>
                            <div className='age-cell'>
                                <h3>Age</h3>
                                <h4>{me.age}</h4>
                            </div>
                        </div>
                        <div className='description-cell'>
                            <h3>Description</h3>
                            <h4>{me.description}</h4>
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
  
  export default Aboutme;