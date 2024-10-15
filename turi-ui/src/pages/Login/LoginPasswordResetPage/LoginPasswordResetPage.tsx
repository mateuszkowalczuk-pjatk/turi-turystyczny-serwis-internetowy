import { useTranslation } from "react-i18next";
import AuthPanel from "../../../components/Auth/AuthPanel";
import AuthTitle from "../../../components/Auth/AuthTitle";
import AuthInput from "../../../components/Auth/AuthInput";
import AuthButton from "../../../components/Auth/AuthButton";
import AuthTopLink from "../../../components/Auth/AuthTopLink";
import AuthDownLink from "../../../components/Auth/AuthDownLink";

const LoginPasswordResetPage = () => {
    const { t } = useTranslation();

    return (
        <AuthPanel
            header={
                <AuthTitle
                    text={t('login-reset.title')}
                />
            }
            option={
                <AuthInput
                    text={t('login-reset.password')}
                />
            }
            input={
                <AuthInput
                    text={t('login-reset.re-password')}
                />
            }
            button={
                <AuthButton
                    text={t('login-reset.button')}
                />
            }
            top={
                <AuthTopLink />
            }
            down={
                <AuthDownLink
                    firstLink={t('login-reset.down')}
                    secondLink={t('center')}
                />
            }
        />
    )
}

export default LoginPasswordResetPage;