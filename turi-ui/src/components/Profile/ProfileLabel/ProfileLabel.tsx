import styles from './ProfileLabel.module.css'
import TextRegular from "../../Controls/Text/TextRegular";

const ProfileLabel = ({ text }: { text: string }) => {
    return (
        <div className={styles.label}>
            <TextRegular
                text={text}
            />
        </div>
    )
}

export default ProfileLabel