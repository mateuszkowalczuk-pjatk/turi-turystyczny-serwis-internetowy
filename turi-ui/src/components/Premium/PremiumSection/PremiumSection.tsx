import styles from './PremiumSection.module.css'
import { ReactNode } from 'react'

interface Props {
    leftPanel?: ReactNode
    rightPanel?: ReactNode
}

const PremiumSection = ({ leftPanel, rightPanel }: Props) => {
    return (
        <div className={styles.section}>
            {leftPanel}
            {rightPanel}
        </div>
    )
}

export default PremiumSection
