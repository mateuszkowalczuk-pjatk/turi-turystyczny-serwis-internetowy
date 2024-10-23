import styles from './ProfileInput.module.css'
import Input from '../../Controls/Input'

const ProfileInput = ({ text }: { text: string }) => {
    return (
        <div className={styles.input}>
            <Input placeholder={text} />
        </div>
    )
}

export default ProfileInput
