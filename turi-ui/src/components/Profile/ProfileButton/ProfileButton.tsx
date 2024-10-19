import { useTranslation } from "react-i18next";
import { GreenButton } from "../../Controls/Button";
import styles from './ProfileButton.module.css'

const ProfileButton = () => {
    const { t } = useTranslation();

    return (
        <div className={styles.button}>
            <GreenButton
                text={t('profile.save')}
            />
        </div>
    )
}

export default ProfileButton;