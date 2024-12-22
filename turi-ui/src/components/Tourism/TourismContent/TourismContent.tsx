import { ReactNode } from 'react'
import styles from './TourismContent.module.css'

interface Props {
    title: ReactNode
    firstPanel: ReactNode
    secondPanel?: ReactNode
    thirdPanel?: ReactNode
    fourthPanel?: ReactNode
    fifthPanel?: ReactNode
}

const TourismContent = ({ title, firstPanel, secondPanel, thirdPanel, fourthPanel, fifthPanel }: Props) => {
    return (
        <div className={styles.content}>
            {title}
            {firstPanel}
            {secondPanel}
            {thirdPanel}
            {fourthPanel}
            {fifthPanel}
        </div>
    )
}

export default TourismContent
