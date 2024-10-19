import { ReactNode } from "react";
import ProfileTitle from "../ProfileTitle";
import ProfilePanel from "../ProfilePanel";
import styles from './ProfileContent.module.css'

const ProfileContent = ({ content }: { content: ReactNode }) => {
    return (
        <div className={styles.content}>
            <ProfileTitle />
            <ProfilePanel
                content={content}
            />
        </div>
    )
}

export default ProfileContent;