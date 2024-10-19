import { useTranslation } from "react-i18next";
import { useNavigate } from "react-router-dom";
import AuthPanel from '../../../components/Auth/AuthPanel'
import AuthTitle from "../../../components/Auth/AuthTitle";
import AuthDescription from "../../../components/Auth/AuthDescription";
import AuthInput from "../../../components/Auth/AuthInput";
import AuthButton from "../../../components/Auth/AuthButton";
import AuthTopLink from "../../../components/Auth/AuthTopLink";
import AuthDownLink from "../../../components/Auth/AuthDownLink";

const SignUpVerifyPage = () => {
    const { t } = useTranslation();
    const navigate = useNavigate();

    const navigateToPersonal = () => {
        navigate('/signup/personal');
    }

    return (
        <AuthPanel
            header={
                <AuthTitle
                    text={t('signup-verify.title')}
                />
            }
            option={
                <AuthDescription
                    text={t('signup-verify.description')}
                />
            }
            input={
                <AuthInput
                    text={t('signup-verify.code')}
                />
            }
            button={
                <AuthButton
                    text={t('signup-verify.button')}
                    onClick={navigateToPersonal}
                />
            }
            top={
                <AuthTopLink
                    text={t('signup-verify.top')}
                />
            }
            down={
                <AuthDownLink
                    firstLink={t('signup-verify.down')}
                    secondLink={'center'}
                />
            }
        />
    )
}

export default SignUpVerifyPage;