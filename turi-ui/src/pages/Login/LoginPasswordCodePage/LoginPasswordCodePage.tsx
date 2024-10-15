import { useTranslation } from "react-i18next";
import AuthPanel from "../../../components/Auth/AuthPanel";
import AuthTitle from "../../../components/Auth/AuthTitle";
import AuthDescription from "../../../components/Auth/AuthDescription";
import AuthInput from "../../../components/Auth/AuthInput";
import AuthButton from "../../../components/Auth/AuthButton";
import AuthTopLink from "../../../components/Auth/AuthTopLink";
import AuthDownLink from "../../../components/Auth/AuthDownLink";

const LoginPasswordCodePage = () => {
    const { t } = useTranslation();

    return (
        <AuthPanel
            header={
                <AuthTitle
                    text={t('login-code.title')}
                />
            }
            option={
                <AuthDescription
                    text={t('login-code.description')}
                />
            }
            input={
                <AuthInput
                    text={t('login-code.code')}
                />
            }
            button={
                <AuthButton
                    text={t('login-code.button')}
                />
            }
            top={
                <AuthTopLink
                    text={t('login-code.top')}
                />
            }
            down={
                <AuthDownLink
                    firstLink={t('login-code.down')}
                    secondLink={t('center')}
                />
            }
        />
    )
}

export default LoginPasswordCodePage;