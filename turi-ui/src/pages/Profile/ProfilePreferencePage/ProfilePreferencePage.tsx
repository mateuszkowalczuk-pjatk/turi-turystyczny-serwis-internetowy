import ProfileLanguage from "../../../components/Profile/ProfileLanguage";
import styles from './ProfilePreferencePage.module.css'

const ProfilePreferencePage = () => {
    return (
        <div className={styles.page}>
            <ProfileLanguage />
        </div>
    )
}

export default ProfilePreferencePage;