import TextRegular from '../../Shared/Controls/Text/TextRegular'
import styles from './ProfileLabel.module.css'

const ProfileLabel = ({ text }: { text: string }) => {
    return (
        <div className={styles.label}>
            <TextRegular text={text} />
        </div>
    )
}

export default ProfileLabel
