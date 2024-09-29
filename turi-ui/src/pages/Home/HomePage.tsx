import HomeLayout from "../../layouts/Home/HomeLayout";
import Header from "../../components/Header";
import Information from "../../components/Information";
import Footer from "../../components/Footer";

const HomePage = () => {
    return (
        <HomeLayout
            header={<Header />}
            information={<Information />}
            footer={<Footer />}
        />
    )
}

export default HomePage;