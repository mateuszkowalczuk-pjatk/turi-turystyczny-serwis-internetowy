import { ReactNode } from "react";
import ProfileHeader from "../ProfileHeader";
import ProfileButton from "../ProfileButton";
import ProfileSection from "../ProfileSection";
import styles from './ProfilePanel.module.css'

const ProfilePanel = ({ content }: { content: ReactNode }) => {
    return (
        <div className={styles.panel}>
            <ProfileHeader />
            <ProfileSection
                content={content}
            />
            <ProfileButton />
        </div>
    )
}

export default ProfilePanel;