import { useNavigate, useLocation } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import TextRegular from "../../Controls/Text/TextRegular";
import styles from './ProfileHeader.module.css';

const ProfileHeader = () => {
    const { t } = useTranslation();
    const navigate = useNavigate();
    const location = useLocation();

    const handleNavigation = (path: string) => {
        navigate(path);
    }

    return (
        <div className={styles.header}>
            <TextRegular
                text={t('profile.account')}
                className={location.pathname === '/profile' ? styles.active : ''}
                onClick={() => handleNavigation('/profile')}
            />
            <TextRegular
                text={t('profile.data')}
                className={location.pathname === '/profile/personal' ? styles.active : ''}
                onClick={() => handleNavigation('/profile/personal')}
            />
            <TextRegular
                text={t('profile.preference')}
                className={location.pathname === '/profile/preference' ? styles.active : ''}
                onClick={() => handleNavigation('/profile/preference')}
            />
        </div>
    )
}

export default ProfileHeader;