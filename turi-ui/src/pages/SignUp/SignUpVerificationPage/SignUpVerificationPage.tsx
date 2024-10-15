import { useTranslation } from "react-i18next";
import AuthPanel from '../../../components/Auth/AuthPanel'
import AuthTitle from "../../../components/Auth/AuthTitle";
import AuthDescription from "../../../components/Auth/AuthDescription";
import AuthInput from "../../../components/Auth/AuthInput";
import AuthButton from "../../../components/Auth/AuthButton";
import AuthTopLink from "../../../components/Auth/AuthTopLink";
import AuthDownLink from "../../../components/Auth/AuthDownLink";

const SignUpVerificationPage = () => {
    const { t } = useTranslation();

    return (
        <AuthPanel
            header={
                <AuthTitle
                    text={t('signup-verification.title')}
                />
            }
            option={
                <AuthDescription
                    text={t('signup-verification.description')}
                />
            }
            input={
                <AuthInput
                    text={t('signup-verification.code')}
                />
            }
            button={
                <AuthButton
                    text={t('signup-verification.button')}
                />
            }
            top={
                <AuthTopLink
                    text={t('signup-verification.top')}
                />
            }
            down={
                <AuthDownLink
                    firstLink={t('signup.down')}
                    secondLink={'center'}
                />
            }
        />
    )
}

export default SignUpVerificationPage;