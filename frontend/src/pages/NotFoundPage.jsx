import React from 'react';

import '../styles/not-found.css';

const NotFoundPage = () => {
  return (
    <div className="content">
      <div className="centered">
        <h1>404 Not Found!</h1>
        <p>
          Sometimes, the things we are searching for elude us not because we are
          unworthy, but because we are meant to find something greater along the
          way. Trust the journey, and remember that every step, even the ones
          that seem lost, brings you closer to where you are meant to be.
        </p>
        <img
          src={`${process.env.PUBLIC_URL}/images/wizard.png`}
          alt="Wizard Image"
        />
      </div>
    </div>
  );
};

export default NotFoundPage;
