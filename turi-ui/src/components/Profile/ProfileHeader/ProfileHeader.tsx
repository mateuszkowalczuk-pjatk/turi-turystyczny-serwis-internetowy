import { useNavigate, useLocation } from 'react-router-dom'
import { useTranslation } from 'react-i18next'
import { useSelector } from 'react-redux'
import TextRegular from '../../Shared/Controls/Text/TextRegular'
import { RootState } from '../../../store/store.ts'
import styles from './ProfileHeader.module.css'

const ProfileHeader = () => {
    const { t } = useTranslation()
    const navigate = useNavigate()
    const location = useLocation()
    const isPremiumAccount = useSelector((state: RootState) => state.premium.isPremiumAccount)

    return (
        <div className={styles.header}>
            <TextRegular
                text={t('profile.account')}
                className={location.pathname === '/profile' ? styles.active : ''}
                onClick={() => navigate('/profile')}
            />
            <TextRegular
                text={t('profile.data')}
                className={location.pathname === '/profile/personal' ? styles.active : ''}
                onClick={() => navigate('/profile/personal')}
            />
            <TextRegular
                text={t('profile.preference')}
                className={location.pathname === '/profile/preference' ? styles.active : ''}
                onClick={() => navigate('/profile/preference')}
            />
            {isPremiumAccount && (
                <TextRegular
                    text={t('profile.premium')}
                    className={location.pathname === '/profile/premium' ? styles.active : ''}
                    onClick={() => navigate('/profile/premium')}
                />
            )}
        </div>
    )
}

export default ProfileHeader
