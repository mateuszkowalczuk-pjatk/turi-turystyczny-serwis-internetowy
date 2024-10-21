import ProfileLabel from '../ProfileLabel'
import ProfileInput from '../ProfileInput'
import styles from './ProfileModule.module.css'

const ProfileModule = ({ text }: { text: string }) => {
    return (
        <div className={styles.module}>
            <ProfileLabel
                text={text}
            />
            <ProfileInput
                text={text}
            />
        </div>
    )
}

export default ProfileModule