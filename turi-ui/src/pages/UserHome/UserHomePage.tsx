import HomeLayout from "../../layouts/Home/HomeLayout";
import UserHeader from "../../components/UserHeader";
import UserInformation from "../../components/UserInformation";
import UserFooter from "../../components/UserFooter";

const UserHomePage = () => {
    return (
        <HomeLayout
            header={<UserHeader />}
            information={<UserInformation />}
            footer={<UserFooter />}
        />
    )
}

export default UserHomePage;