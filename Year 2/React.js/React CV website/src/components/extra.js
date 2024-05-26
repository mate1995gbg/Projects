import { useEffect, useState } from 'react';
import '../App.css';

function Extra() {
    //get info from SOS100 API
    const [sponsorOffer, setSponsorOffer]=useState([])
    useEffect(() => {
      fetch('https://informatik4.ei.hv.se/offer/SponsorOffers') 
      .then((response) => response.json())
      .then((actualData) => { 
        console.log(actualData) 
        setSponsorOffer(actualData) 
      })
      .catch((err) => { 
        console.log(err.message);
      });
    }, []); 

    return (
      <div className="App">
        <h1>Show all sponsorOffers from SOS100 group exercise:</h1>
            {sponsorOffer.map((offer) => {
                return (
                        <div className='infocell-style'>
                            <div className='infocell-header'>
                                <div className='name-cell'>
                                    <h3>SponsorOffer name:</h3>
                                    <h4>{offer.title}</h4>
                                </div>
                                <div className='age-cell'>
                                    <h3>Description</h3>
                                    <h4>{offer.description}</h4>
                                </div>
                            </div>
                            <div className='description-cell'>
                                <h3>Link to Offer:</h3>
                                <h4>{offer.link}</h4>
                            </div>     
                        </div>
                        )
                    }
                )
            }
      </div>
    );
  }
  
  export default Extra;