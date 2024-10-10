import { useTranslation } from "react-i18next";
import TextRegular from "../../../Text/TextRegular";
import styles from './AccountInformation.module.css'

const AccountInformation = () => {
    const { t } = useTranslation();

    return (
        <div className={styles.account}>
            <TextRegular
                text={t('information.sign-up-text')}
            />
        </div>
    )
}

export default AccountInformation;