import { useState, useEffect } from 'react';
import { Owner } from './Owner.ts'

const OwnerList = () => {
  const [owners, setOwners] = useState<Owner[]>([]);

  useEffect(() => {
    fetchUser().then(() => console.log());
  }, []);

  const fetchUser = async () => {
    await fetch('http://localhost:8080/getOwners')
      .then(response => response.json())
      .then(data => setOwners(data))
      .catch(error => console.log(error));
  }

  return (
    <div>
      <h1>Owners</h1>
      <ol>
        {owners.map(owner => (
          <li key={owner.ownerId}>
              <h3>{owner.firstName} {owner.lastName}</h3>
              <p>{owner.description}</p>
              <h5>Phone number - {owner.phoneNumber}</h5>
              <h5>Email - {owner.email}</h5>
          </li>
        ))}
      </ol>
    </div>
  );
};

export default OwnerList;
