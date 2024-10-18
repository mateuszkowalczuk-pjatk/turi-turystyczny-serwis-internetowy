import { ReactNode } from "react";
import styles from './ProfileContent.module.css'
import ProfileTitle from "../ProfileTitle";

const ProfileContent = ({ content }: { content: ReactNode }) => {
    return (
        <div className={styles.content}>
            <ProfileTitle />
            {/*obramówka i w niej jeszcze opcje do wyboru i dopiero pod nimi out...*/}
            { content }
        </div>
    )
}

export default ProfileContent;