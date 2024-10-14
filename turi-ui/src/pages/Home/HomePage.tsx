import HomeLayout from '../../layouts/Home/HomeLayout'
import Information from '../../components/Information'

const HomePage = () => {
    // const { isAuthenticated } = useContext(AuthContext);

    return (
        <HomeLayout
            content={<Information />}
        />
    )
}

export default HomePage;