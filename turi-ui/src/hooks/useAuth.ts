// import Cookies from 'js-cookie';
// import { useState, useEffect } from 'react';
// import useFetch from './useFetch'; // Twój custom hook
//
// const useAuth = () => {
//     const [isAuthenticated, setIsAuthenticated] = useState(false);
//     const accessToken = Cookies.get('accessToken');
//
//     // Funkcja do odświeżania tokena
//     const refreshToken = async () => {
//         const response = await useFetch('/auth/refresh-token', 'POST', {
//             token: Cookies.get('refreshToken'),
//         });
//
//         if (response.data) {
//             // Zapisz nowy access token w ciasteczkach
//             Cookies.set('accessToken', response.data.newAccessToken, { secure: true, sameSite: 'strict' });
//             setIsAuthenticated(true);
//         } else {
//             setIsAuthenticated(false);
//         }
//     };
//
//     useEffect(() => {
//         if (accessToken) {
//             setIsAuthenticated(true);
//         } else {
//             refreshToken(); // Odśwież token przy starcie aplikacji, jeśli accessToken jest nieważny
//         }
//     }, [accessToken]);
//
//     return { isAuthenticated, refreshToken };
// };
//
// export default useAuth;
