import { ReactNode } from "react";
import styles from './ProfileContent.module.css'

const ProfileContent = ({ content }: { content: ReactNode }) => {
    return (
        <div className={styles.content}>
            { content }
        </div>
    )
}

export default ProfileContent;