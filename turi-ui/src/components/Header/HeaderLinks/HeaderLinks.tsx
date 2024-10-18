import { ReactNode } from "react";
import styles from './HeaderLinks.module.css'

interface Props {
    firstLink: ReactNode;
    secondLink: ReactNode;
    thirdLink: ReactNode;
    fourthLink?: ReactNode;
}

const HeaderLinks = ({ firstLink, secondLink, thirdLink, fourthLink }: Props) => {
    return (
        <div className={styles.links}>
            { firstLink }
            { secondLink }
            { thirdLink }
            { fourthLink }
        </div>
    )
}

export default HeaderLinks