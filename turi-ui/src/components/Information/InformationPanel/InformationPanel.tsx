import { ReactNode } from "react";
import styles from './InformationPanel.module.css'

const InformationPanel = ({ content }: { content: ReactNode }) => {
    return (
        <div className={styles.panel}>
            { content }
        </div>
    )
}

export default InformationPanel