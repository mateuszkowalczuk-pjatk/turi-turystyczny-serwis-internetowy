import { ReactNode } from 'react'
import styles from './ProfileSection.module.css'

const ProfileSection = ({ content }: { content: ReactNode }) => {
    return (
        <div className={styles.section}>
            { content }
        </div>
    )
}

export default ProfileSection