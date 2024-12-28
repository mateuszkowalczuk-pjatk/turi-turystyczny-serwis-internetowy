import { ReactNode } from 'react'
import styles from './MainInformationContent.module.css'

interface Props {
    title: ReactNode
    content: ReactNode
    option?: ReactNode
}

const MainInformationContent = ({ title, content, option }: Props) => {
    return (
        <div className={styles.content}>
            {title}
            {content}
            {option}
        </div>
    )
}

export default MainInformationContent
