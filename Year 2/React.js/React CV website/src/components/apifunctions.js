import React, { useRef, useState } from "react";

function Api() {
  const baseURL = "https://informatik4.ei.hv.se/marcoReactAPI/api";

  const get_id = useRef(null);
  const get_title = useRef(null);

  const [getResult, setGetResult] = useState(null);

  const formatResponse = (res) => {
    return JSON.stringify(res, null, 2);
  }

  async function getAllProgrammingLanguages() {
    try {
      const res = await fetch(`${baseURL}/ProgrammingLanguage`);

      if (!res.ok) {
        const message = `An error has occured: ${res.status} - ${res.statusText}`;
        throw new Error(message);
      }

      const data = await res.json();

      const result = {
        status: res.status + "-" + res.statusText,
        headers: {
          "Content-Type": res.headers.get("Content-Type"),
          "Content-Length": res.headers.get("Content-Length"),
        },
        length: res.headers.get("Content-Length"),
        data: data,
      };

      setGetResult(formatResponse(result));
    } catch (err) {
      setGetResult(err.message);
    }
  }

  async function getProgrammingLanguageById() {
    const id = get_id.current.value;

    if (id) {
      try {
        const res = await fetch(`${baseURL}/ProgrammingLanguage/${id}`);

        if (!res.ok) {
          const message = `An error has occured: ${res.status} - ${res.statusText}`;
          throw new Error(message);
        }

        const data = await res.json();

        const result = {
          data: data,
          status: res.status,
          statusText: res.statusText,
          headers: {
            "Content-Type": res.headers.get("Content-Type"),
            "Content-Length": res.headers.get("Content-Length"),
          },
        };

        setGetResult(formatResponse(result));
      } catch (err) {
        setGetResult(err.message);
      }
    }
  }

  const clearGetOutput = () => {
    setGetResult(null);
  }

  return (
    <div className="card">
      <div className="card-body">
        <div className="input-group input-group-sm">
          <button className="btn btn-sm btn-primary" onClick={getAllProgrammingLanguages}>Get All</button>

          <input type="text" ref={get_id} className="form-control ml-2" placeholder="Id" />
          <div className="input-group-append">
            <button className="btn btn-sm btn-primary" onClick={getProgrammingLanguageById}>Get by Id</button>
          </div>

          <button className="btn btn-sm btn-warning ml-2" onClick={clearGetOutput}>Clear</button>
        </div>   
        
        { getResult && <div className="alert alert-secondary mt-2" role="alert"><pre>{getResult}</pre></div> }
      </div>
    </div>
  );
}

export default Api;