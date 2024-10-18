import FooterCopyright from '../FooterCopyright'
import styles from './ProfileFooter.module.css'

const ProfileFooter = () => {
    return (
        <div className={styles.profile}>
            <FooterCopyright />
        </div>
    )
}

export default ProfileFooter;