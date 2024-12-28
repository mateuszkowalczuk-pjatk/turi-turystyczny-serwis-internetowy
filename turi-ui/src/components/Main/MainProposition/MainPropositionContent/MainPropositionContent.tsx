import { ReactNode } from 'react'
import styles from './MainPropositionContent.module.css'

interface Props {
    title: ReactNode
    panels: ReactNode
}

const MainPropositionContent = ({ title, panels }: Props) => {
    return (
        <div className={styles.content}>
            {title}
            {panels}
        </div>
    )
}

export default MainPropositionContent
