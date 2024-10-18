import { ReactNode } from "react";
import styles from './InformationContent.module.css'

interface Props {
    title: ReactNode;
    content: ReactNode;
    option?: ReactNode;
}

const InformationContent = ({ title, content, option }: Props) => {
    return (
        <div className={styles.content}>
            { title }
            { content }
            { option }
        </div>
    )
}

export default InformationContent;