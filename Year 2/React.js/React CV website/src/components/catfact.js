import { useEffect, useState } from 'react';
import '../App.css';

function CatFact() {

    //get random cat fact from external API
    const [asdCat, setFact]=useState([])
    useEffect(() => {
        fetch('https://catfact.ninja/fact') 
        .then((response) => response.json())
        .then((actualData) => { 
        console.log(actualData) 
        setFact(actualData) 
        })
        .catch((err) => { 
        console.log(err.message);
        });
    }, []); 

    return (
      <div className="App">
        <h1>Show Random cat fact:</h1>
            {[asdCat].map((fact) => {
                return (
                        <div className='infocell-style'>
                            <div className='infocell-header'>
                                <div className='name-cell'>
                                    <h4>{fact.fact}</h4>
                                </div>  
                            </div> 
                        </div>
                        )
                    }
                )
            }
      </div>
    );
  }
  
  export default CatFact;