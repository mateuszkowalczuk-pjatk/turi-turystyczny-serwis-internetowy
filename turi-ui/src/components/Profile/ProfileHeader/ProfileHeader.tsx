import { useHooks } from '../../../hooks/shared/useHooks.ts'
import { useStates } from '../../../hooks/shared/useStates.ts'
import TextRegular from '../../Shared/Controls/Text/TextRegular'
import styles from './ProfileHeader.module.css'

const ProfileHeader = () => {
    const { t, navigate, location } = useHooks()
    const { isPremium } = useStates()

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
            {isPremium && (
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
