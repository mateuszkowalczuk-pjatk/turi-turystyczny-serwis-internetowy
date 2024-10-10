import AuthLayout from "../../layouts/Auth";
import Registration from "../../components/Registration";

const SignUpPage = () => {
    return (
        <AuthLayout
            content={<Registration />}
        />
    )
}

export default SignUpPage;