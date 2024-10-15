import { useTranslation } from "react-i18next";
import SignUpPanel from "../../../components/Auth/SignUpPanel/SignUpPanel.tsx";
import AuthTitle from "../../../components/Auth/AuthTitle";
import AuthInput from "../../../components/Auth/AuthInput";
import AuthButton from "../../../components/Auth/AuthButton";
import AuthTopLink from "../../../components/Auth/AuthTopLink";
import AuthDownLink from "../../../components/Auth/AuthDownLink";

const SignUpPage = () => {
    const { t } = useTranslation();

    return (
        <SignUpPanel
            header={
                <AuthTitle
                    text={t('signup.title')}
                />
            }
            login={
                <AuthInput
                    text={t('signup.login')}
                />
            }
            email={
                <AuthInput
                    text={t('signup.email')}
                />
            }
            password={
                <AuthInput
                    text={t('signup.password')}
                />
            }
            rePassword={
                <AuthInput
                    text={t('signup.re-password')}
                />
            }
            button={
                <AuthButton
                    text={t('signup.button')}
                />
            }
            top={ <AuthTopLink /> }
            down={
                <AuthDownLink
                    firstLink={t('signup.signin')}
                    secondLink={'right'}
                />
            }
        />
    )
}

export default SignUpPage;